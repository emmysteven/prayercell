import { AuthService } from '@app/core/services'

export function appInitializer(authService: AuthService) {
  let user = JSON.parse(<string>localStorage.getItem('user') || '{}');
  return () => new Promise(resolve => {
    // attempt to refresh token on app start up to auto authenticate
    authService
      .refreshToken(user.refreshToken)
      .subscribe((data) => {
        localStorage.setItem('user', JSON.stringify(data));
      })
      .add(resolve);
  });
}
