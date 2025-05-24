# Autenticación con GitHub en Angular usando Firebase

Este documento describe cómo integrar autenticación con GitHub en un proyecto Angular utilizando Firebase Authentication.

## Requisitos

- Node.js y Angular CLI instalados
- Cuenta de GitHub
- Proyecto Angular creado (`ng new mi-proyecto`)
- Cuenta en [Firebase Console](https://console.firebase.google.com/)

---

## Paso 1: Crear un Proyecto en Firebase

1. Ir a [Firebase Console](https://console.firebase.google.com/)
2. Hacer clic en **Agregar proyecto**.
3. Darle un nombre al proyecto y seguir los pasos hasta crearlo.

---

## Paso 2: Agregar una Aplicación Web

1. En el panel del proyecto Firebase, haz clic en **</>** para agregar una app web.
2. Ponle un nombre (por ejemplo: `angular-auth-app`) y da clic en **Registrar app**.
3. Copia la configuración que Firebase te da (la necesitarás luego).

---

## Paso 3: Instalar dependencias en Angular

```bash
cd mi-proyecto
npm install firebase @angular/fire
```

---

## Paso 4: Configurar Firebase en el proyecto

Editar el archivo `src/environments/environment.ts`:

```ts
export const environment = {
  production: false,
  firebaseConfig: {
    apiKey: "TU_API_KEY",
    authDomain: "TU_DOMINIO.firebaseapp.com",
    projectId: "ID_DEL_PROYECTO",
    storageBucket: "BUCKET.appspot.com",
    messagingSenderId: "SENDER_ID",
    appId: "APP_ID"
  }
};
```

Luego, importar y configurar Firebase en `app.module.ts`:

```ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';

import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { provideAuth, getAuth } from '@angular/fire/auth';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    provideFirebaseApp(() => initializeApp(environment.firebaseConfig)),
    provideAuth(() => getAuth())
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
```

---

## Paso 5: Habilitar autenticación con GitHub en Firebase

1. Ir a Firebase Console > Autenticación > Métodos de inicio de sesión
2. Habilitar **GitHub**
3. Te pedirá **Client ID** y **Client Secret**

---

## Paso 6: Crear una aplicación OAuth en GitHub

1. Ir a [https://github.com/settings/developers](https://github.com/settings/developers)
2. Crear una nueva OAuth App
3. En **Homepage URL** pon `https://TU_DOMINIO.firebaseapp.com`
4. En **Authorization callback URL** pon `https://TU_DOMINIO.firebaseapp.com/__/auth/handler`
5. Copia el **Client ID** y **Client Secret** y pégalos en Firebase

---

## Paso 7: Crear servicio de autenticación

Crear un servicio: `ng generate service services/auth`

```ts
import { Injectable } from '@angular/core';
import { GithubAuthProvider, signInWithPopup, signOut, Auth } from '@angular/fire/auth';
import { inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private auth: Auth = inject(Auth);

  loginWithGitHub() {
    return signInWithPopup(this.auth, new GithubAuthProvider());
  }

  logout() {
    return signOut(this.auth);
  }

  get currentUser() {
    return this.auth.currentUser;
  }
}
```

---

## Paso 8: Usar el servicio en un componente

En `app.component.ts`:

```ts
import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  template: `
    <button (click)="login()">Login con GitHub</button>
    <button (click)="logout()">Logout</button>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}

  login() {
    this.auth.loginWithGitHub().then(res => {
      console.log('Login exitoso', res.user);
    }).catch(err => {
      console.error('Error de login', err);
    });
  }

  logout() {
    this.auth.logout();
  }
}
```

---

## Paso 9: Proteger rutas (opcional)

Crear un guard para rutas protegidas si es necesario.

---

## Listo 🎉

Ya tienes autenticación con GitHub funcionando en tu aplicación Angular.

---

## Recursos

- [Firebase Auth Docs](https://firebase.google.com/docs/auth)
- [GitHub OAuth Apps](https://docs.github.com/en/apps/oauth-apps)
- [AngularFire Docs](https://angularfire.dev/)
