package com.example.mymovies.presntation.HomePage;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2 = {"Lcom/example/mymovies/presntation/HomePage/HomeViewmodel;", "Landroidx/lifecycle/ViewModel;", "movieRepo", "Lcom/example/mymovies/domain/repository/MoviesRepository;", "(Lcom/example/mymovies/domain/repository/MoviesRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mymovies/presntation/HomePage/HomeUiState;", "prevSortId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "setUiState", "(Lkotlinx/coroutines/flow/StateFlow;)V", "onEvent", "", "event", "Lcom/example/mymovies/presntation/HomePage/HomeEvents;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class HomeViewmodel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.mymovies.domain.repository.MoviesRepository movieRepo = null;
    private int prevSortId = 1;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mymovies.presntation.HomePage.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private kotlinx.coroutines.flow.StateFlow<com.example.mymovies.presntation.HomePage.HomeUiState> uiState;
    
    @javax.inject.Inject
    public HomeViewmodel(@org.jetbrains.annotations.NotNull
    com.example.mymovies.domain.repository.MoviesRepository movieRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.example.mymovies.presntation.HomePage.HomeUiState> getUiState() {
        return null;
    }
    
    public final void setUiState(@org.jetbrains.annotations.NotNull
    kotlinx.coroutines.flow.StateFlow<com.example.mymovies.presntation.HomePage.HomeUiState> p0) {
    }
    
    public final void onEvent(@org.jetbrains.annotations.NotNull
    com.example.mymovies.presntation.HomePage.HomeEvents event) {
    }
}