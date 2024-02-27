import { CanActivateFn } from '@angular/router';

export const roleAuthGuard: CanActivateFn = (route, state) => {
  let pathRole = route.url[0].path
  let sessionRole = sessionStorage.getItem('role');
  if (sessionRole!=null && sessionRole.toLowerCase() == pathRole) {
    return true;
  }
  return false;
};
