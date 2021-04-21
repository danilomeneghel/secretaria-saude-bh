// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/hubsmsa-backend/api/v1',  
  clientId: 'hubsmsa',
  urlKeycloak: 'https://acesso-st.pbh.gov.br/auth',
  realm: 'PBH',
  jwtSecret: '6fccda93-b32c-4624-b4fe-c4505167419e'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
