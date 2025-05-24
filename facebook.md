
# Autenticación con Facebook en Angular usando Firebase

Esta guía te muestra cómo implementar autenticación con Facebook en un proyecto Angular utilizando Firebase Authentication.

## Prerrequisitos

- Tener una cuenta de Facebook y una app registrada en el [Facebook Developers Portal](https://developers.facebook.com/).
- Tener un proyecto Angular configurado.
- Tener un proyecto de Firebase creado y vinculado a tu proyecto Angular.

---

## Paso 1: Crear una App en Facebook

1. Ve a [Facebook Developers](https://developers.facebook.com/apps/) y crea una nueva app.
2. Selecciona **Consumer** como tipo de app.
3. Una vez creada, ve a **Settings > Basic**:
   - Copia el **App ID** y el **App Secret**.
   - Agrega tu dominio en **App Domains** (ej. `localhost` o tu dominio real).
   - Configura la **Privacy Policy URL**, **Terms of Service URL** y **Icon** (puedes usar URLs dummy para desarrollo).
4. En el menú lateral, selecciona **Facebook Login > Settings**:
   - En **Valid OAuth Redirect URIs**, agrega:  
     ```
     https://<YOUR_FIREBASE_PROJECT>.firebaseapp.com/__/auth/handler
     ```

---

## Paso 2: Habilitar Facebook en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/).
2. Selecciona tu proyecto > **Authentication** > **Sign-in method**.
3. Habilita **Facebook**:
   - Pega tu **App ID** y **App Secret**.
   - Agrega la URL de redirección OAuth desde la app de Facebook.

---

## Paso 3: Instalar Firebase en Angular

```bash
npm install firebase @angular/fire
```

---

## Paso 4: Configurar Firebase en tu app Angular

### `src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  firebase: {
    apiKey: 'TU_API_KEY',
    authDomain: 'TU_DOMINIO.firebaseapp.com',
    projectId: 'TU_PROJECT_ID',
    storageBucket: 'TU_BUCKET.appspot.com',
    messagingSenderId: 'TU_SENDER_ID',
    appId: 'TU_APP_ID',
  },
};
```

### `app.module.ts`

```ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { provideAuth, getAuth } from '@angular/fire/auth';
import { AppComponent } from './app.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAuth(() => getAuth())
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
```

---

## Paso 5: Implementar Login con Facebook

### `auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Auth, signInWithPopup, FacebookAuthProvider } from '@angular/fire/auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private auth: Auth) {}

  loginWithFacebook() {
    const provider = new FacebookAuthProvider();
    return signInWithPopup(this.auth, provider);
  }

  logout() {
    return this.auth.signOut();
  }
}
```

### `app.component.ts`

```ts
import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  template: `
    <button (click)="login()">Login con Facebook</button>
    <button (click)="logout()">Logout</button>
  `,
})
export class AppComponent {
  constructor(private authService: AuthService) {}

  login() {
    this.authService.loginWithFacebook().then(cred => {
      console.log('Usuario logueado:', cred.user);
    });
  }

  logout() {
    this.authService.logout();
  }
}
```

---

## ¡Listo!

Ya tienes autenticación con Facebook integrada en tu proyecto Angular usando Firebase Authentication.
