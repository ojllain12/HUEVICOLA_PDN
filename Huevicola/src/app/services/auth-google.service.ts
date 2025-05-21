import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root'
})
export class AuthGoogleService {

  constructor(private oAuthService: OAuthService,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID

  ) {
    this.initLogin();
  }

  initLogin() {
    if (isPlatformBrowser(this.platformId)) {
      const config: AuthConfig = {
        issuer: 'https://accounts.google.com',
        strictDiscoveryDocumentValidation: false,
        /*redirectUri: window.location.origin + '/main',*/
        redirectUri: 'http://localhost:65534/login/oauth2/code/google',
        clientId: '201383001952-sjv0p1vbk439ptc9vhdahpq35o1hs03g.apps.googleusercontent.com',
        scope: 'openid profile email',
        responseType: 'code',
        requireHttps: false,
        showDebugInformation: true,
      }
      this.oAuthService.configure(config);
      this.oAuthService.setupAutomaticSilentRefresh();
      this.oAuthService.loadDiscoveryDocumentAndTryLogin();
    }
  }
  login() {
    this.oAuthService.initLoginFlow();
  }
  logout() {
    this.oAuthService.logOut();
  }
  getProfile() {
    return this.oAuthService.getIdentityClaims();
  }

}
