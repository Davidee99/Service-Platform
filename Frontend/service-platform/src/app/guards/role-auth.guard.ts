import { CanActivateFn } from '@angular/router';

export const roleAuthGuard: CanActivateFn = (route, state) => {
  console.error(route.url[0].path);
  let pathRole = route.url[0].path
  let sessionRole = sessionStorage.getItem('role');
  if (sessionRole!=null && sessionRole.toLowerCase() == pathRole) {
    return true;
  }
  return false;
};
