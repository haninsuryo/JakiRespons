package com.example.jakirespons.data

class MoviesRepository(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) {

    suspend fun getAll(): List<Movie> {
        val apiService = movieApiService.getPopular()
        val movies = ArrayList<Movie>()
        apiService.results?.forEach { data ->
            movies.add(
                Movie(
                    "https://image.tmdb.org/t/p/original${data?.posterPath}",
                    data?.title ?: "-",
                    data?.voteAverage ?: 0.0,
                    data?.id ?: 0
                )
            )
        }

        return movies
    }

    suspend fun get(content_id: Int): Movie {
        val details = movieApiService.getDetails(content_id)
        val releaseDates = movieApiService.getReleaseDates(content_id)
        val credits = movieApiService.getCredits(content_id)

        var rated = ""
        releaseDates.results?.filter { it?.iso31661 == "US" }?.forEach {
            it?.releaseDates?.forEach rated@{
                rated = it?.certification ?: "-"
                return@rated
            }
        }

        val genre = details.genres?.joinToString(", ") { it?.name ?: "-" } ?: "-"

        val director = credits.crew?.filter { it?.job == "Director" }?.joinToString(", ") {
            it?.name ?: "-"
        } ?: "-"

        val writers = credits.crew?.filter { it?.department == "Writing" }?.joinToString(", ") {
            it?.name ?: "-"
        } ?: "-"

        val stars = credits.cast?.take(10)?.joinToString(", ") { it?.name ?: "-" } ?: "-"

        val tvMovie = Movie(
            "https://image.tmdb.org/t/p/original${details.posterPath}",
            details.title ?: "-",
            details.voteAverage ?: 0.0,
            details.id ?: 0,
            rated,
            Utils.minToString(details.runtime ?: 0),
            genre,
            details.releaseDate ?: "-",
            details.overview ?: "-",
            director,
            writers,
            stars,
        )

        return tvMovie
    }

    suspend fun getFavorite() : List<Movie> = movieDao.getData()

    suspend fun deleteFavorite(movie: Movie) = movieDao.delete(movie)

    suspend fun setFavorite(movie: Movie) : Long = movieDao.insert(movie)

    suspend fun isFavoriteExist(id: Int?) : Boolean = movieDao.isExist(id)
}