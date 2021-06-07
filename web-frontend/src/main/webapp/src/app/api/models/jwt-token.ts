/* tslint:disable */
/* eslint-disable */

/**
 * Unencoded JWT toekn
 */
export interface JwtToken {

  /**
   * JWT access toekn
   */
  access_token?: string;

  /**
   * Token expiration
   */
  expires_in?: number;

  /**
   * The UUID of the the host record.
   */
  id?: string;

  /**
   * The referesh token
   */
  refresh_token?: string;

  /**
   * The token scope
   */
  scope?: string;

  /**
   * The type of access token
   */
  token_type?: string;
}
