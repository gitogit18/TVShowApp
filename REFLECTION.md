# Written Reflection

## 1. Which part of your submission are you least confident about, and why?

The part of my submission that I'm least confident about is about the unit testing part of my submission. I have not had much experience working with testing procedures, so I initially was not completely sure what the requirement of having at least two unit tests for the ViewModel or data layer was trying to achieve.
Instead of only trying to make the tests pass, I tried to understand the purpose of the tests and what behavior they should verify. I used different sources to learn about unit testing, coroutine testing, and testing ViewModels, and then verified whether my implementation followed the expected approach. This helped me understand that the tests should verify the behavior of my ViewModel, rather than simply testing implementation details.

## 2. Describe a moment during this project (or any past project) where you got completely stuck. What did you do, step by step?
One moment was when my unit tests failed with an `android.util.Log` error saying that the method was not mocked.

First, I checked the test output to identify where the failure occurred. I then traced the problem back to the `HomeViewModel` and found that it was using `Log.d()`. I researched the difference between local JVM tests and Android runtime code, then asked AI for guidance. I learned that `android.util.Log` is part of the Android framework and is not available in a normal local unit test environment. I removed the unnecessary debug logging and ran the tests again. After that, I also corrected an incorrect expected alphabetical order in one of the generated tests. Both tests eventually passed.

## 3. Imagine: it's Thursday, your task is due Friday, and you realize you misunderstood the requirement, half your work is wrong. What are you doing now?
I would immediately stop working on what I am currently doing and look back at the requirements to identify what I misunderstood and which parts of my work are affected.

After that, I would plan what needs to be changed, estimate what it would take to fix the mistakes, and prioritize the most important requirements first. I would also talk to the person in charge, such as a PM or mentor, to discuss the situation and make sure I understand the expected adjustments correctly. From there, I would focus on making the necessary changes to achieve the original requirements.

## 4. Your mentor asks you to change an approach you believe is worse. What do you do?
I would first ask why they believe the alternative approach is better and try to understand the reasoning behind the change.
If I still disagree, I would explain my reasoning and provide specific technical trade-offs rather than simply rejecting the suggestion. However, if the mentor has more context about the project's requirements, constraints, or long-term direction, I would take that into consideration and adjust my approach if their reasoning makes more sense in that context.

## 5. What's something technical you taught yourself recently outside of class/work, and how did you learn it?

Recently, I have been learning more about mobile development and its ecosystem. I took online courses to learn Kotlin and Jetpack Compose, as well as Flutter and Dart. I also learned through documentation, blogs such as Medium, YouTube tutorials, and experimenting with personal projects.
It has not always been a smooth journey because I often have to solve errors, try different solutions, and adapt my implementation to the needs of different projects. I also use AI to help me understand problems and make better technical decisions.
I found that repeatedly building things and facing errors helped me learn these technologies better than only reading theoretical explanations.


