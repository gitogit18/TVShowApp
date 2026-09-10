# AI Usage Log

This document records how AI was used as a supporting tool during the development of this project. I generally implemented and tested an approach first, then used AI to help troubleshoot problems, evaluate decisions, or guide the next implementation step.

## 1. Project Architecture & API Integration

**What I did first:**  
I started setting up the Android project and considered how to organize the application and connect it to the TVMaze API.

**What I asked AI / Problem:**  
After establishing the basic project structure, I asked AI for guidance on structuring the API layer and organizing the application using a suitable architecture.

**What AI gave me:**  
AI suggested using MVVM with a Repository layer, Retrofit for API communication, Kotlin coroutines, and separate data models for the TVMaze response.

**What I did:**  
I used the suggestion as a guide and implemented the Model, View, ViewModel, and Repository structure. I then tested the API integration and adjusted the models based on the actual API response.

**What I verified / What AI got wrong:**  
I verified the actual TVMaze API response and found that `rating.average` can be null. I therefore added nullable handling instead of assuming that every show has a rating.

---

## 2. UI Implementation & State Handling

**What I did first:**  
I implemented the main show list and detail screens using Jetpack Compose and connected them to the ViewModel.

**What I asked AI / Problem:**  
After getting the basic UI working, I asked AI how to properly handle the required loading, error, retry, and success states and how to connect the screens through Navigation Compose.

**What AI gave me:**  
AI suggested representing the UI state using a sealed `UiState` and handling each state explicitly in the Compose UI. It also guided me in passing the show ID through navigation to the Detail screen.

**What I did:**  
I implemented the suggested state handling and navigation, then tested the application by navigating between the list and detail screens.

**What I verified / What AI got wrong:**  
The first Detail navigation implementation resulted in a blank screen because the destination had not yet been implemented. I verified the navigation by first displaying the received show ID before completing the Detail screen implementation.

---

## 3. Share Feature & Debugging

**What I did first:**  
I implemented the Detail screen and its required information, then started working on the share functionality.

**What I asked AI / Problem:**  
After implementing the basic share action, I asked AI for guidance on formatting the shared content and using Android's native sharing mechanism.

**What AI gave me:**  
AI suggested using `Intent.ACTION_SEND` with `Intent.EXTRA_TEXT` and including the show's title, summary, and URL.

**What I did:**  
I implemented the share Intent and formatted the content into separate sections for the title, summary, and URL. I then tested it using the Android share sheet.

**What I verified / What AI got wrong:**  
The initial shared text appeared messy in the share preview because the summary was long. I adjusted the formatting and verified the final result through the native Android share sheet.

---

## 4. Unit Testing & AI-Assisted Debugging

**What I did first:**  
After completing the main functionality, I created unit tests for the `HomeViewModel` to verify its behavior.

**What I asked AI / Problem:**  
I asked AI for guidance on creating two unit tests for the ViewModel, including success and error scenarios.

**What AI gave me:**  
AI suggested using `kotlinx-coroutines-test`, a fake API implementation, and tests for successful data loading and API failure.

**What I did:**  
I adapted the tests to my actual `HomeViewModel`, which processes the API response into `HomeUiContent` containing featured, highest-rated, by-year, and alphabetical lists.

**What I verified / What AI got wrong:**  
The first test failed because my ViewModel contained `android.util.Log`, which is not available in local JVM unit tests. I identified and removed the debug logging. The generated test also contained an incorrect expected alphabetical order, which I corrected based on the actual sorting logic. After these changes, both unit tests passed.
