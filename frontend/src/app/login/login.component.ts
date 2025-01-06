import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from './authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  validateForm: FormGroup;
  userEmail: string = '';
  isLoggedIn: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthenticationService) {
    this.validateForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      remember: [true]
    });
  }

  ngOnInit(): void {
    this.checkUserStatus();
  }

  checkUserStatus(): void {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
      this.userEmail = JSON.parse(currentUser).email;
      this.isLoggedIn = true;
    }
  }

  submitForm(): void {
    if (this.validateForm.valid) {
      const { email, password } = this.validateForm.value;
      this.authService.login(email, password).subscribe((res: any) => {
        this.userEmail = email;
        this.isLoggedIn = true;
        localStorage.setItem('currentUser', JSON.stringify(res));
      }, error => {
        console.error('Login failed:', error);
        // Afficher un message d'erreur à l'utilisateur si la connexion échoue
      });
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        control.markAsDirty();
        control.updateValueAndValidity({ onlySelf: true });
      });
    }
  }

  logout(): void {
    this.isLoggedIn = false;
    localStorage.removeItem('currentUser');
  }
}
