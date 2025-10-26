import { Component, signal } from '@angular/core';
import { MovieSearchComponent } from './movie-search/movie-search.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [CommonModule, MovieSearchComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('movie-demo-project');
}
