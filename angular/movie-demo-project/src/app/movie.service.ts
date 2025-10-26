import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Movie {
  id: string | null;
  title: string;
  year: number;
  genre?: string;
  cast?: string[];
  genres?: string[];
  href?: string;
  extract?: string;
  thumbnail?: string;
  thumbnail_width?: number;
  thumbnail_height?: number;
}

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  
  private apiUrl = 'http://localhost:8080/backend/search';

  constructor(private http: HttpClient) {}

  searchMovies(query: string): Observable<Movie[]> {

    const headers = new HttpHeaders({
      'content-type': 'application/json'
    });

    return this.http.get<Movie[]>(`${this.apiUrl}?${query}`, { headers });
  }

}
