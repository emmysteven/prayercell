import { AuthService } from '@app/core/services'

export function appInitializer(authService: AuthService) {
  const user = JSON.parse(localStorage.getItem('user')!);
  return () => new Promise(resolve => {
    // attempt to refresh token on app start up to auto authenticate
    authService
      .refreshToken(user.refreshToken)
      .subscribe()
      .add(resolve);
  });
}
