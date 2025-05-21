import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Auth, FacebookAuthProvider, fetchSignInMethodsForEmail, GithubAuthProvider, GoogleAuthProvider, signInWithEmailAndPassword, signInWithPopup } from '@angular/fire/auth';
import { StorageService } from '../../services/storage.service.js';
import { NotificationService } from '../common/notification.service.js';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private auth: Auth,
    private storage: StorageService,
    private notification: NotificationService) {
    this.storage.deleteToken();
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.nullValidator]],
    });
  }

  async onLogin(): Promise<void> {
    const { email, password } = this.loginForm.value!;
    try {
      const result = await signInWithEmailAndPassword(this.auth, email!, password!);
      const user = result.user;
      const token = await user.getIdToken();
      //console.log('Usuario:', user);
      //console.log('Token Firebase JWT:', token);
      this.storage.addToken(token);
      this.router.navigate(['/main']);
    } catch (err: any) {
      this.validateError(err);
    }
  }

  async loginWithAccounts(type: string) {
    let provider = null;
    switch (type) {
      case 'github':
        provider = new GithubAuthProvider();
        break;
      case 'facebook':
        provider = new FacebookAuthProvider();
        break;
    }
    try {
      const result = await signInWithPopup(this.auth, provider ? provider : new GoogleAuthProvider());
      const user = result.user;
      const token = await user.getIdToken();
      //console.log('Usuario:', user);
      //console.log('Token Firebase JWT:', token);
      this.storage.addToken(token);
      this.router.navigate(['/main']);
    } catch (err: any) {
      this.validateError(err);
    }
  }

  async validateError(err: any) {
    if (err.code !== 'auth/cancelled-popup-request') {
      this.notification.showError(err?.message ?? 'Error al iniciar sesión');
    }
    console.error(err);
  }
}
