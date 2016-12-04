# HelloRxJavaRetrolambda
This is a example how to use RxJava and Retrolambda
<p align="center">
    <img src="https://github.com/CabezasGonzalezJavier/HelloRxJavaRetrolambda/blob/master/helloRxJavaRetrolambda.jpg" alt="Web Launcher"/>
</p>

  Java 8 introduced Lambdas Expressions, unfortunately Android does not support Java 8, so we are not able to take advantage of this with RxJava. Luckily there is a library called **[Retrolambda](https://github.com/orfjackal/retrolambda)** which backports lambdas to previous versions of Java. There is also a **[gradle plugin](https://github.com/evant/gradle-retrolambda)** for Retrolambda that will allow the use of lambdas in an Android application.
 
 In this RxJava there are diferent elements:
  * BASIC(Observable)
  * ASYNCHRONOUS
  * SINGLES
  * SUBJECTS
  * MAP
  * DEBOUNCES
  
  
  Basic
---------
  This method creates an Observable such that when an Observer subscribes, the onNext() of the Observer is immediately called with the argument provided to Observable.just(). The onCompleted() will then be called since the Observable has no other values to emit.

```java  
Observable<List<String>> listObservable = Observable.just(getColorList());

        listObservable.subscribe(new Observer<List<String>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> colors) {
                mSimpleStringAdapter.setStrings(colors);
            }
        });

```
  Asynchronous
---------
  If we use it with Observable.just(), mRestClient.getFavoriteTvShows() will be evaluated immediately and block the UI thread. Enter the Observable.fromCallable() method. It gives us two important things:
     * The code for creating the emitted value is not run until someone subscribes to the Observer.
     * The creation code can be run on a different thread.
```java
Observable<List<String>> tvShowObservable = Observable.fromCallable(() -> mRestClient.getFavoriteTvShows());
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
  Singles
---------
  There’s a simpler version of an Observable called a Single. Singles work almost exactly the same as Observables. But instead of there being an onCompleted(), onNext(), and onError(), there are only two callbacks:
     * onSuccess() and onError().
```java
Single<List<String>> tvShowSingle = Single.fromCallable(() -> mRestClient.getFavoriteTvShows());

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
  Subjects
---------
  Subjects are special objects that are both an Observable and an Observer.
  With a PublishSubject, as soon as you put something in one end of the pipe it immediately comes out the other.
```java
PublishSubject<Integer> mCounterEmitter.subscribe(new Observer<Integer>() {
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
  It increments a variable called mCounter. It calls onNext() on the mCounterEmitter with the new value of mCounter.
```java
mCounter ++;
mCounterEmitter.onNext(mCounter);
```
  Map
---------
  It's a function that takes in one value and outputs another value. Usually there is some relationship between value put in to the map and the value that is output.
 ```java
Single.just(4).map((Integer integer) -> String.valueOf(integer))

                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String value) {
                        mValueDisplay.setText(value);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
```        
  Bringing it All Together
---------
  Everything together and a new concept: debounce. Let’s dive in. If you want to setup a PublishSubject such that it receives values the user types into a search box, fetches a list of suggestions based on that query, and then displays them. 
 ```java
 Single.just(4).map((Integer integer) -> String.valueOf(integer))

                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String value) {
                        mValueDisplay.setText(value);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
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
