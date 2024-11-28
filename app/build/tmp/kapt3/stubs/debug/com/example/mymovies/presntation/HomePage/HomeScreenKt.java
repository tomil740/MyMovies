package com.example.mymovies.presntation.HomePage;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a$\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a&\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a,\u0010\r\u001a\u00020\u00012\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a$\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u00a8\u0006\u0015"}, d2 = {"HomeScreen", "", "onItemNav", "Lkotlin/Function1;", "", "beers", "Lcom/example/mymovies/presntation/HomePage/HomeScreenStateAndEvents;", "HomeTopBar", "userName", "isLoggedIn", "", "onLoginClick", "Lkotlin/Function0;", "MovieList", "beersData", "Landroidx/paging/compose/LazyPagingItems;", "Lcom/example/mymovies/domain/models/MovieListItem;", "SortMenu", "selectedOption", "", "onSortSelected", "app_debug"})
public final class HomeScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemNav, @org.jetbrains.annotations.NotNull
    com.example.mymovies.presntation.HomePage.HomeScreenStateAndEvents beers) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MovieList(@org.jetbrains.annotations.Nullable
    androidx.paging.compose.LazyPagingItems<com.example.mymovies.domain.models.MovieListItem> beersData, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onItemNav) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HomeTopBar(@org.jetbrains.annotations.NotNull
    java.lang.String userName, boolean isLoggedIn, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoginClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SortMenu(int selectedOption, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onSortSelected) {
    }
}