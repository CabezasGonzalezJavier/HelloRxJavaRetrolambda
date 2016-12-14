# HelloRxJavaRetrolambda
This is a example how to use RxJava and Retrolambda.

A simple implementation of the Model-View-Presenter pattern with no architectural frameworks.

* **[Android architecture](https://github.com/googlesamples/android-architecture)**


Each feature has:

* A contract defining the view and the presenter.
* An Activity which is responsible for the creation of fragments and presenters
* A Fragment which implements the view interface.
* A presenter which implements the presenter interface.

In general, the business logic lives in the presenter and relies on the view to do the Android UI work.

The view contains almost no logic: it converts the presenter's commands to UI actions and listens to user actions, which are passed to the presenter.

Contracts are interfaces used to define the connection between views and presenters.



Libraries
---------
The project is setup using:

 * **[Butter Knife](https://github.com/JakeWharton/butterknife)**
 * **[RxJava](https://github.com/ReactiveX/RxJava)**
 * **[RxAndroid](https://github.com/ReactiveX/RxAndroid)**
 * **[RxBinding](https://github.com/JakeWharton/RxBinding)**
 * **[Retrolambda](https://github.com/orfjackal/retrolambda)**
 * **[gradle plugin](https://github.com/evant/gradle-retrolambda)**
 * **[Retrofit](https://github.com/square/retrofit)**
 * **[GSON](https://github.com/google/gson)**
 * **[Guava (checking null)](https://github.com/google/guava)**
 
 
<p align="center">
    <img src="https://github.com/CabezasGonzalezJavier/HelloRxJavaRetrolambda/blob/master/helloRxJavaRetrolambda.jpg" alt="Web Launcher"/>
</p>
**[You can see, this post in The Developer world is yours](http://thedeveloperworldisyours.com/android/rxjava-retrolambda/#sthash.e4rHACNL.dpbs)**
  
  Java 8 introduced Lambdas Expressions, unfortunately Android does not support Java 8, so we are not able to take advantage of this with RxJava. Luckily there is a library called **[Retrolambda](https://github.com/orfjackal/retrolambda)** which backports lambdas to previous versions of Java. There is also a **[gradle plugin](https://github.com/evant/gradle-retrolambda)** for Retrolambda that will allow the use of lambdas in an Android application.
 
 In RxJava there are different elements:
 <pre>
 Simple
    1.BASIC(Observable)
    2.ASYNCHRONOUS
    3.SINGLES
    4.SUBJECTS
    5.MAP
    6.DEBOUNCES
 Complex
    7.Flat Map
    8.Concat Map
    
</pre>

# Simple

  Basic
---------
  This method creates an Observable such that when an Observer subscribes, the onNext() of the Observer is immediately called with the argument provided to Observable.just(). The onCompleted() will then be called since the Observable has no other values to emit:

```java  
    Observable<List<String>> listObservable = Observable.just(getColorList());

    listObservable.subscribe(new Observer<List<String>>() { 

            @Override 
            public void onCompleted() { } 

            @Override 
            public void onError(Throwable e) { } 

            @Override
            public void onNext(List<String> colors) {
                mSimpleStringAdapter.setStrings(colors);
            }
    });
```
whit lambda:
```java  
    Observable<List<String>> listObservable = Observable.just(getColorList());

    listObservable.subscribe(
                (List<String> colors)-> mSimpleStringAdapter.setStrings(colors),
                (error) -> {},
                () -> {});
```
  Asynchronous
---------
  If we use it with Observable.just(), mRestClient.getFavoriteTvShows() will be evaluated immediately and block the UI thread. Enter the Observable.fromCallable() method. It gives us two important things:
     * The code for creating the emitted value is not run until someone subscribes to the Observer.
     * The creation code can be run on a different thread.
```java
    Observable<List<String>> tvShowObservable = Observable.fromCallable(new Callable<List<String>>() { 

        @Override 
        public List<String> call() { 
            return mRestClient.getFavoriteTvShows(); 
        }
    });
    
    mTvShowSubscription = tvShowObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<String> tvShows) {
                                displayTvShows(tvShows);
                            }
                        });
```
whit lambda:
```java  
    Observable<List<String>> tvShowObservable = Observable.fromCallable(() -> mRestClient.getFavoriteTvShows());
    mTvShowSubscription = tvShowObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                (List<String> tvShows) -> displayTvShows(tvShows),
                (error) -> {},
                () -> {});
```

  Singles
---------
  There’s a simpler version of an Observable called a Single. Singles work almost exactly the same as Observables. But instead of there being an onCompleted(), onNext(), and onError(), there are only two callbacks:
     * onSuccess() and onError().
```java
    Single<List<String>> tvShowSingle = Single.fromCallable(new Callable<List<String>>() { 

        @Override
        public List<String> call() throws Exception {
            mRestClient.getFavoriteTvShows(); 
        }
    });

    mTvShowSubscription = tvShowSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> tvShows) {
                        displayTvShows(tvShows);
                    }

                    @Override
                    public void onError(Throwable error) {
                        displayErrorMessage();
                    }
                }); 
```
whit lambda:
```java  
    Single<List<String>> tvShowSingle = Single.fromCallable(() -> mRestClient.getFavoriteTvShows());

    mTvShowSubscription = tvShowSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<String> tvShows) -> displayTvShows(tvShows),
                    (Throwable error) ->  displayErrorMessage());
```

  Subjects
---------
  Subjects are special objects that are both an Observable and an Observer.
  With a PublishSubject, as soon as you put something in one end of the pipe it immediately comes out the other.
```java
    PublishSubject<Integer> mCounterEmitter = PublishSubject.create();
    mCounterEmitter.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                mCounterDisplay.setText(String.valueOf(integer));
            }
        });
```
whit lambda:
```java  
    PublishSubject<Integer>  mCounterEmitter = PublishSubject.create();
    mCounterEmitter.subscribe(
                (Integer integer) ->  mCounterDisplay.setText(String.valueOf(integer)),
                (Throwable e) ->{},
                () -> { });
```

  It increments a variable called mCounter. It calls onNext() on the mCounterEmitter with the new value of mCounter.
```java
    mCounter ++;
    mCounterEmitter.onNext(mCounter);
```

  Map
---------
  It's a function that takes in one value and outputs another value. Usually there is some relationship between value put in to the map and the value that is output.
 ```java
    Single.just(4).map(new Func1<Integer, String>() { 
    
        @Override 
        public String call(Integer integer) { 
            return String.valueOf(integer);
        } 
    }).subscribe(new SingleSubscriber<String>() { 
    
        @Override 
        public void onSuccess(String value) { 
            mValueDisplay.setText(value); 
        } 
    
        @Override 
        public void onError(Throwable error) { } 
        });
```
with lambda:
```java
    Single.just(4).map((Integer integer) -> String.valueOf(integer))

                .subscribe((String value) -> mValueDisplay.setText(value),
                        (Throwable error) -> {});
```

  All Together with debounce
---------
  Everything together and a new concept: debounce. Let’s dive in. If you want to setup a PublishSubject such that it receives values the user types into a search box, fetches a list of suggestions based on that query, and then displays them. 
 ```java
    mSearchResultsSubject = PublishSubject.create();
    mTextWatchSubscription = mSearchResultsSubject
        .debounce(400, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.io())
        .map(new Func1<String, List<String>>() {
    
            @Override 
            public List<String> call(String s) { 
                return mRestClient.searchForCity(s); 
            } 
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<String>>() { 
   
            @Override 
            public void onCompleted() { }

            @Override 
            public void onError(Throwable e) { } 

            @Override
            public void onNext(List<String> cities) {
                handleSearchResults(cities); 
            }
        });
    });
 ``` 
with lambda:
```java
    mSearchResultsSubject = PublishSubject.create();
    mTextWatchSubscription = mSearchResultsSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map( (String string) -> mRestClient.searchForCity(string))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<String> cities) -> handleSearchResults(cities),
                        (Throwable e) -> {},
                        () -> {});
```

# Complex

We have an Observable emitting a set of Integers and we want to calculate the square of each of those values:

```java
public class DataManager {
    private final Executor jobExecutor;
    public DataManager() {
     this.numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10));
     jobExecutor = JobExecutor.getInstance();
    }
 
    public Observable<Integer> getNumbers() {
     return Observable.from(numbers);
    }
 
    public List<Integer> getNumbersSync() {
     return this.numbers;
    }
    
    public Observable<Integer> squareOfAsync(int number) {
        return Observable.just(number * number).subscribeOn(Schedulers.from(jobExecutor));
    }
}
```

 Concat Map
 ---------
  It's a operator that operate on the entire sequence of items emitted by an Observable, emit the emissions from two or more Observables without interleaving them
 ```java
 .concatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return mDataManager.squareOfAsync(integer);
                    }
                })
  ```
  with :: operator
  ```java
  .concatMap(mDataManager::squareOfAsync)
  ```
  
  Flat Map
  ---------
  The flatMap() method creates a new Observable by applying a function that you supply to each item emitted by the original Observable, where that function is itself an Observable that emits items, and then merges the results of that function applied to every item emitted by the original Observable, emitting these merged results.
  
 ```java
 .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return mDataManager.squareOfAsync(integer);
                    }
                })
  ```
with :: operator
  ```java
  .flatMap(mDataManager::squareOfAsync)
  ```
  flatMap() uses merge operator while concatMap() uses concat operator meaning that the last one cares about the order of the elements
  
  
# Further reading

  As the above only gives a rough overview of rxjava example I'd strongly recommend checking out the following:
  * **[RXJava Documentation](http://reactivex.io/documentation/operators.html#combining)**
  * **[part 1](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850#.4zr72tozz)** by Kurtis Nusbaum
  * **[part 2](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-2-6e877af352#.wpvo34c77)** by Kurtis Nusbaum
  * **[retrofit-rxjava](http://randomdotnext.com/retrofit-rxjava)**  by Kai
  * **[concatMap() vs flatMap()](http://fernandocejas.com/2015/01/11/rxjava-observable-tranformation-concatmap-vs-flatmap/)** by Fernando Cejas
  * **[The Developer world is yours](http://thedeveloperworldisyours.com/android/rxjava-retrolambda/#sthash.e4rHACNL.dpbs)** 

# Requirements

    - Android Studio

    - Gradle

# Installation

    - Install Android Studio:

    https://developer.android.com/sdk/installing/index.html

    - Install gradle:

    http://gradle.org/docs/current/userguide/installation.html

# Usage
    Compile with Android Studio and gradle


Feel free to contribute, and contact me for any issues.

Developed By
------------
* Javier González Cabezas - <javiergonzalezcabezas@gmail.com>

<a href="https://es.linkedin.com/in/javier-gonz%C3%A1lez-cabezas-8b4b2231">
  <img alt="Add me to Linkedin" src="https://github.com/JorgeCastilloPrz/EasyMVP/blob/master/art/linkedin.png" />
</a>

License
-------

    Copyright 2016 Javier González Cabezas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
