package pe.edu.unmsm.fisi.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.unmsm.fisi.moviecatalogservice.models.CatalogItem;
import pe.edu.unmsm.fisi.moviecatalogservice.models.Movie;
import pe.edu.unmsm.fisi.moviecatalogservice.models.UserRating;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@GetMapping(path = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		UserRating ratings = this.restTemplate.getForObject(
				"http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
		
		return ratings.getUserRatings().stream().map(rating -> {
//			Movie movie = this.restTemplate.getForObject(
//					"http://localhost:8082/movie/" + rating.getMovieId(), Movie.class);
			Movie movie = this.webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movie/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
			
			return new CatalogItem(movie.getName(), "desc", rating.getRating());
		}).collect(Collectors.toList());
	}

}
