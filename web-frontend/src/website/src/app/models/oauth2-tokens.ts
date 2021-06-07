/**
 * OAuth2 Tokens from token exchange
 */
export interface OAuth2Tokens {
  access_token?: string;
  expires_in?: number;
  id_token?: string;
  refresh_token?: string;
  token_type?: string;
}
