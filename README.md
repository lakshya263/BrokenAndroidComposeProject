# Broken Android Compose Project

Final Bug Fix & Architecture Documentation

Candidate: Lakshya Srivastava
Role: Android Intern – Greedy Game

## Task List (As Provided)
	1.	Fix crashes and freezes
	2.	Implement navigation to the detail screen
	3.	Make network calls off the main thread
	4.	Replace GlobalScope usage with lifecycle-aware scope
	5.	Implement local persistence (Room) and use it as cache
	6.	Correct JSON parsing / DTO mapping
	7.	Add loading/error/empty states properly
	8.	Remove memory leak (do not use static Activity references)
	9.	Implement updateArticle to persist edits
	10.	Improve project structure (move business logic out of composables)

## Bug 1: App crashes due to null values
Before
```
Text(article.title)
```
After
```
title = dto.title.orEmpty()
```

Fix Explanation

Nullable values returned from the API were being passed directly into Jetpack Compose Text, which does not accept null values and caused runtime crashes.
Null handling was added at the UI level and later centralized in the data layer.

## Bug 2: UI freezes and skipped frames
Before
```
Thread.sleep(2000)
```
After 
```
withContext(Dispatchers.IO) {
    // background work
}
```

Fix Explanation

Blocking calls on the main thread caused UI freezes and skipped frames. All blocking work was moved to a background dispatcher (Dispatchers.IO).

## Bug 3: Network calls on the main thread
Before
```
fun fetchArticlesBlocking(): List<Article>
```
After
```
suspend fun fetchArticles(): List<Article>
```
Fix Explanation

Blocking network calls were converted into suspend functions and executed off the main thread using coroutines.

## Bug 4: Improper use of GlobalScope
Before
```
GlobalScope.launch {
    fetchArticles()
}
```
After
```
LaunchedEffect(Unit) {
    fetchArticles()
}
```
Fix Explanation

GlobalScope ignores lifecycle and can cause leaks.
It was replaced with LaunchedEffect, which is lifecycle-aware and automatically cancelled.

## Bug 5: Navigation worked but detail screen was blank
Before
```
Scaffold {
    NewsScreen()
}
```
After 
```
Scaffold { padding ->
    AppNavHost(modifier = Modifier.padding(padding))
}
```
Fix Explanation

Scaffold padding was not applied, causing content to render behind the top bar.
Passing padding fixed visibility issues.

## Bug 6: Unsafe JSON parsing
Before
```
List<Map<String, Any>>
```
After 
```
data class Article(
    val title: String?,
    val author: String?
)
```
Fix Explanation

Using untyped maps was unsafe and error-prone.
Strongly typed DTOs ensure safe and predictable JSON parsing.

## Bug 7: Missing loading, error, and empty states
Before
```
LazyColumn {
    // content only
}
```
After
```
when {
    loading -> CircularProgressIndicator()
    error != null -> Text("Error occurred")
    articles.isEmpty() -> Text("No articles found")
    else -> LazyColumn { ... }
}
```

Fix Explanation

The UI previously showed no feedback during loading or errors.
Proper UI states were added to improve UX.

### Bug 8: Memory leak due to static Activity reference
Before
```
companion object {
    var leakedActivity: MainActivity? = null
}
```
After 
```
// Removed completely
```

Fix Explanation

Static references to Activity prevent garbage collection and cause memory leaks.
The reference was removed entirely.

## Bug 9: No local persistence (Room not implemented)
Before
```
// No database layer
```
After
```
@Entity
data class ArticleEntity(...)

@Dao
interface ArticleDao { ... }

@Database
abstract class AppDatabase : RoomDatabase()
```
Fix Explanation

Room was implemented to provide offline support and caching using a cache-first strategy.

## Architecture Improvements
	•	Business logic moved out of composables
	•	Repository pattern introduced
	•	UI made stateless and reactive
	•	Network, database, and UI layers clearly separated

## Final Outcomes
	•	App is crash-free
	•	Network calls are non-blocking
	•	Lifecycle-safe coroutine usage
	•	Offline caching via Room
	•	Proper navigation and UI states
	•	Clean, maintainable architecture

All assignment requirements have been successfully completed.

## Conclusion

This project now follows modern Android best practices and demonstrates strong debugging, architectural reasoning, and practical Jetpack Compose development skills suitable for an Android Intern role.



