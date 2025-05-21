import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  protected KEY_TOKEN = 'user_token';

  deleteToken() {
    if (typeof window !== 'undefined' && localStorage) {
      localStorage.clear();
    }
  }

  addToken(data: string) {
    if (typeof window !== 'undefined' && localStorage) {
      localStorage.setItem(this.KEY_TOKEN, data);
    }
  }

  getToken(): string | null {
    if (typeof window !== 'undefined' && localStorage) {
      return localStorage.getItem(this.KEY_TOKEN);
    }
    return null;
  }
}
