import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Movie {
  id: string | null;
  title: string;
  year: number;
  genre?: string;
  cast?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  
  private apiUrl = 'http://localhost:8080/backend/search';

  constructor(private http: HttpClient) {}

  searchMovies(query: string): Observable<Movie[]> {

    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa('msadmin:StrongSecret123')
    });

    return this.http.get<Movie[]>(`${this.apiUrl}?${query}`, { headers });
  }

  searchTest(): Observable<{ message: string }> {
    return this.http.get<{ message: string }>('http://localhost:8080');
  }
}
