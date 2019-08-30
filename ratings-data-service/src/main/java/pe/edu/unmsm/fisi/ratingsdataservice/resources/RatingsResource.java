package pe.edu.unmsm.fisi.ratingsdataservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.unmsm.fisi.ratingsdataservice.models.Rating;
import pe.edu.unmsm.fisi.ratingsdataservice.models.UserRating;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

	@GetMapping(path = "/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@GetMapping(path = "/user/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> userRatings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 4)
		);
		
		UserRating userRating = new UserRating();
		userRating.setUserRatings(userRatings);
		return userRating;
	}

}
