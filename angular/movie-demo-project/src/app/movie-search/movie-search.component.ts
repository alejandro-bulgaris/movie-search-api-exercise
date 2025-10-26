import { Component } from '@angular/core';
import { MovieService, Movie } from '../movie.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrls: ['./movie-search.component.scss'],
  imports: [ReactiveFormsModule],
  standalone: true
})
export class MovieSearchComponent {
  query: string = '';
  movies: Movie[] = [];
  loading: boolean = false;

  constructor(private movieService: MovieService) {}

  searchForm = new FormGroup({
    title: new FormControl(''),
    year: new FormControl(''),
    genre: new FormControl('')
  });


  async search() {
    this.loading = true;
    let formGroupValue = this.searchForm.value;

    console.log(formGroupValue);

    let queryParts = [];
    if (formGroupValue.title) queryParts.push(`title=${encodeURIComponent(formGroupValue.title)}`);
    if (formGroupValue.year) queryParts.push(`year=${encodeURIComponent(formGroupValue.year)}`);
    if (formGroupValue.genre) queryParts.push(`genre=${encodeURIComponent(formGroupValue.genre)}`);
    this.query = queryParts.join('&');
    if (!this.query) { 
      this.movies = [];
      this.loading = false;
      return; 
    }

    try {
      const data = await firstValueFrom(this.movieService.searchMovies(this.query));
      console.log('Movies fetched:', data);
      /**
       * @remarks
       * Note there is a max number of records set to 25 in the backend for demoing purposes but generaly a large dataset table would use pagination or lazy loading
       */
      this.movies = data;
    } catch (error) {
      alert('Error fetching movies');
    } finally {
      this.loading = false;
      console.log('AJAX call completed');
    }

  }

}
