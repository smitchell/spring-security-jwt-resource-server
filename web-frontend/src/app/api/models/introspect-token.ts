/* tslint:disable */
/* eslint-disable */

/**
 * Values from token introspection
 */
export interface IntrospectToken {

  /**
   * True if the token is active.
   */
  active?: boolean;
  exp?: string;

  /**
   * The token scope
   */
  scope?: string;

  /**
   * The subject
   */
  sub?: string;
}
