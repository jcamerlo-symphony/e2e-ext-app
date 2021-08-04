import {NavigationController} from './services/navigation.js';

// Initialize driver services
const navigationController = new NavigationController();
const baseUrl = `${ window.location.protocol }`;

let appToken = undefined;
let appTokenPromise = fetch(`${baseUrl}/appToken`)
  .then((res) => res.json())
  .then((res) => {
    appToken = res['token'];
  });

Promise.all([appTokenPromise, SYMPHONY.remote.hello()]).then((data) => {
  SYMPHONY.application
    .register(
      { appId: 'e2e-ext-app', tokenA: appToken },
      // import services to be used anywhere in the extension
      ['ui', 'dialogs', 'applications-nav'],
      // export services by their names
      [navigationController.serviceName],
    )
    .then(function (response) {});
});
