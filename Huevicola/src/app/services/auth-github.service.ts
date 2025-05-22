import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GithubAuthService  {
  loginWithGithub(): void {
    window.location.href = `https://github.com/login/oauth/authorize` +
      `?client_id=${'Ov23lis7nSID63muZdCl'}` +
      `&redirect_uri=${encodeURIComponent('http://localhost:65535/callback/github')}` +
      `&scope=${encodeURIComponent('read:user user:email')}`;
  }
}
