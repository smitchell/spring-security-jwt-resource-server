/* tslint:disable */
/* eslint-disable */

/**
 * Service Build Info
 */
export interface BuildInfo {

  /**
   * The build artificat name.
   */
  artifact: string;

  /**
   * The service group
   */
  group: string;

  /**
   * The service name
   */
  name: string;

  /**
   * The build time
   */
  time?: string;

  /**
   * The build version.
   */
  version: string;
}
