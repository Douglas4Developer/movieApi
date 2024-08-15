package com.movielix.movieApi.service;

import com.movielix.movieApi.dto.MovieDto;
import com.movielix.movieApi.entities.Movie;
import com.movielix.movieApi.exceptions.FileExistsException;
import com.movielix.movieApi.exceptions.MovieNotFoundException;
import com.movielix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService  {

    private final MovieRepository movieRepository;

    private final FileService fileService;


    @Value("${project.poster}")
    private String path;


    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }


    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. upload the file
       if (Files.exists(Paths.get( path + File.pathSeparator +file.getOriginalFilename()))){
           throw new FileExistsException("File already exists(Arquivo ja existe)! Please choose another file name!.");
       }
        String uploadFileName = fileService.uploadFile(path, file);

        // 2. set the value of field 'poster' as fileName
        movieDto.setPoster(uploadFileName);

        // 3.  map dto to movie object
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        // 4. save the movie object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. generate the posterUrl
        String posterUrl = baseUrl + "/file/" + uploadFileName;


        // 6. map movie object to DTO object and  return it

        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
                );

        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        //1. Check the data in DB and if exists, fetch the data of given ID
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + movieId));

        //2. generate posterUrl
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        //3. map to MovieDto object and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {

        //1.fetch(busca todos) all data from DB
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = new ArrayList<>();

        //2. Interate  through the list, generate posterUIrl for each movie obj,
        // and map to MovieDto obj

        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. check if movie object exists with given movieId

        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + movieId));

        // 2. if File is null, do nothing
        // if file is not null, then delete existing file associated with the record,
        // and upload the new file

        String fileName = mv.getPoster();

        if (file != null){
            Files.deleteIfExists(Paths.get(path + File.pathSeparator + fileName));
            fileName = fileService.uploadFile(path, file);

        }

        // 3. set movieDto's poster valuem according to step2

        movieDto.setPoster(fileName);

        // 4. map it to Movie object

        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());


        // 5. save the movie object -> return saved movie object

        Movie updatedMovie = movieRepository.save(movie);

        // 6. generate posterurl for it

        String posterUrl = baseUrl + "/file/" + fileName;

        // 7. map to MovieDto and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // 1. check if movie object exists in DB

        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + movieId));
        Integer id = mv.getMovieId();
        // 2. delete the file associeated with this  object
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));


        // 3. delete the movie object

        movieRepository.delete(mv);


        return "Movie deleted with id = " + id;
    }
}
