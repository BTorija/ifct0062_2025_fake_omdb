package com.beatorija.ifct0062_2025_fake_omdb.controller;

import com.beatorija.ifct0062_2025_fake_omdb.model.Movie;
import com.beatorija.ifct0062_2025_fake_omdb.service.interfaces.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies/")
public class MovieController {
    private MovieService movieService;
    public MovieController (MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie>createMovie(@RequestBody Movie movie){
        Movie newMovie = movieService.create(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getMovieId(@PathVariable String id){
        Movie movie = movieService.findById(id);
        if (movie==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(Map.of(
                "data", movie,"message","movie found"

        ));
    }

    @GetMapping("/search")
    public ResponseEntity<List> findMovies(@RequestParam(name="s", required = false) String titles,
                                           /*Buscar por coincidencia parcial*/
                                           @RequestParam(name="i", required = false) String imdbId,
                                            /*Buscar por ID exacto*/
                                           @RequestParam(name="t", required = false) String title)
                                            /*Buscar por título exacto*/
    {
        if (titles!=null) {
            return ResponseEntity.ok().body(movieService.findMoviesByTitle(titles));
        } else if (imdbId !=null) {
            List<Movie> movies = new ArrayList<>();
            movies.add(movieService.findById(imdbId));
            return ResponseEntity.ok().body(movies);
        } else if (title != null) {
            List<Movie> movies = new ArrayList();
            movies.add(movieService.findByTitle(title));
            return ResponseEntity.ok().body(movies);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}
