import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend_Stage1.0';

  ngOnInit() {
    // Attach the beforeunload event listener to clear localStorage
    window.addEventListener('beforeunload', this.clearLocalStorage);
  }

  clearLocalStorage(event: Event) {
    // Clear local storage
    localStorage.clear();
  }
}
