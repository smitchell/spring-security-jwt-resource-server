import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
declare const process: any; // Typescript compiler will complain without this

import { AppModule } from './app/app.module';

if (process.env.NODE_ENV === 'prod') {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
