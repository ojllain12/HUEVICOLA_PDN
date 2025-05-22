import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from '../../views/common/notification.service';
import { UserServices } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-github',
  standalone: true,
  imports: [],
  templateUrl: './github.component.html',
  styleUrl: './github.component.css'
})
export class GithubComponent implements OnInit {

  private alreadyProcessed = false;

  constructor(private route: ActivatedRoute,
    private notification: NotificationService,
    private router: Router,
    private user: UserServices,
    private storage: StorageService
  ) {
    notification.showLoading("Autenticando");
  }

  ngOnInit(): void {
    this.route.queryParams
    .subscribe(params => {
      if (this.alreadyProcessed) {
        console.log('⚠️ Ya procesado, ignorando');
        return;
      }
      this.alreadyProcessed = true;
      const code = params['code'];

      if (!code) {
        this.notification.showError('Error al autenticar con Github');
        this.router.navigate(['/']);
        return;
      }
      this.user.loginWithGithub(code)
      .pipe(first()).
      subscribe({
        next: (user) => {
          this.notification.hideLoading();
          this.storage.addToken(user);
          this.router.navigate(['/'], { queryParams: {}, replaceUrl: true });
        },
        error: (err: any) => {
          this.notification.hideLoading();
          this.notification.showError(err.error.message ? err.error.message : 'Error durante autenticación con GitHub');
          this.router.navigate(['/'], { replaceUrl: true });
        }
      });
    });
  }
}
