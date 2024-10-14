import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MdbFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'front-end';
}
