created by Oleksandr Morozov

app consists from 2 activity and 3 fragments

## Tech stack & Open-source libraries

- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- [Coin ]for dependency injection.
- JetPack
    - LiveData - notify domain layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.

- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)

- [Retrofit2 ]  - construct the REST APIs and paging network data.
- [Picasso] - loading images.
- [TransformationLayout] - implementing transformation motion animations.
- [Material-Components](https://github.com/material-components/material-components-android) -
  Material design components like cardView.
- Custom Views
- [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView,
  fully customizable with animations.
