import { AuthService } from '@app/core/services'

export function appInitializer(authService: AuthService) {
  return () => new Promise(resolve => {
    // attempt to refresh token on app start up to auto authenticate
    authService
      .refreshToken()
      .subscribe()
      .add(resolve);
  });
}
