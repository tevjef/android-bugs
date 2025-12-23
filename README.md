# Circuit Bug Reproducer

Minimal Android-only project to reproduce a bug where `rememberSaveable` state in `Presenter.present()` is not restored when navigating back, causing state loss. This is a regression with Compose Runtime 1.10.0.

## Bug Details

**Issue**: `rememberSaveable` state in `Presenter.present()` methods is not being restored when navigating back to a screen.

**Affected Versions**:
- Circuit: 0.31.0
- Compose Runtime: `androidx.compose.runtime:runtime:1.10.0`
- Compose Runtime Saveable: `org.jetbrains.compose.runtime:runtime-saveable:1.10.0-rc02`
- Also broken with: `androidx.compose.runtime:runtime-saveable:1.10.0`

**Status**: Works correctly with Compose Runtime 1.9.x, broken with 1.10.0

## Bug Reproduction

1. Launch the app
2. Click "Increment" to increase the counter (state saved via `rememberSaveable`)
3. Click "Next Screen" to navigate to a new `CounterScreen`
4. Press back to return to the previous screen
5. **Expected**: Counter state should be restored (showing the previous count, e.g., 5)
6. **Actual**: Counter state is reset to 0

The issue occurs because `rememberSaveable` in `CounterPresenter.present()` is not properly restoring state when the screen is navigated back to. No errors are thrown; this is silent state loss.

## Technical Details

The bug manifests in `CounterPresenter.present()` where `rememberSaveable` is used:

```kotlin
@Composable
override fun present(): CounterScreen.CounterState {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    // State not restored when navigating back
}
```