import {NgModule} from "@angular/core";

declare const process: any; // Typescript compiler will complain without this

import {EnvVariables} from "./environment-variables.token";
import {prodVariables} from "./prod";
import {localDevVariables} from "./local-dev";


export function environmentFactory() {
  switch (process.env.NODE_ENV) {
    case 'prod':
      return prodVariables;
    default:
      return localDevVariables;
  }
}

@NgModule({
  providers: [
    {
      provide: EnvVariables,
      // useFactory instead of useValue so we can easily add more logic as needed.
      useFactory: environmentFactory
    }
  ]
})
export class EnvironmentsModule {
}
