const NavigationControllerServiceName = 'e2eextapp:navigation';
export class NavigationController {
  serviceName = 'e2eextapp:navigation';
  constructor() {
    SYMPHONY.services.make(this.serviceName, this, ['execute'], true);
  }

  execute = ({ action, payload }) => {
    const navigationApi = SYMPHONY.services.subscribe('applications-nav');
    switch (action) {
      case 'ADD': {
        navigationApi.add(payload?.id, payload?.title, this.serviceName);
        break;
      }
      case 'REMOVE': {
        navigationApi.remove(payload?.id);
        break;
      }
    }
  };
}
