# HelloRxJavaRetrolambda
This is a example how to use RxJava and Retrolambda
<p align="center">
    <img src="https://github.com/CabezasGonzalezJavier/HelloRxJavaRetrolambda/blob/master/helloRxJavaRetrolambda.jpg" alt="Web Launcher"/>
</p>

  Java 8 introduced Lambdas Expressions, unfortunately Android does not support Java 8, so we are not able to take advantage of this with RxJava. Luckily there is a library called **[Retrolambda](https://github.com/orfjackal/retrolambda)** which backports lambdas to previous versions of Java. There is also a **[gradle plugin](https://github.com/evant/gradle-retrolambda)** for Retrolambda that will allow the use of lambdas in an Android application.
 
 In RxJava there are different elements:
    1.BASIC(Observable)
    2.ASYNCHRONOUS
    3.SINGLES
    4.SUBJECTS
    5.MAP
    6.DEBOUNCES

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
 
  Libraries
---------
The project is setup using:
 * **[Butter Knife](https://github.com/JakeWharton/butterknife)**
 * **[RxJava](https://github.com/ReactiveX/RxJava)**
 * **[RxAndroid](https://github.com/ReactiveX/RxAndroid)**
 * **[Retrolambda](https://github.com/orfjackal/retrolambda)**
 * **[gradle plugin](https://github.com/evant/gradle-retrolambda)**

# Further reading

  As the above only gives a rough overview of rxjava example I'd strongly recommend checking out the following:
  * **[part 1](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850#.4zr72tozz)** by Kurtis Nusbaum
  * **[part 2](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-2-6e877af352#.wpvo34c77)** by Kurtis Nusbaum
  * **[rxandroidexamples](https://github.com/klnusbaum/rxandroidexamples)**  by Kurtis Nusbaum

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
