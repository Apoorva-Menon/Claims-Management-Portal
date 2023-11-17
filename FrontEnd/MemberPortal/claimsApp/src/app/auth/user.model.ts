export class User {
  constructor(
    public username: string,
    private _token: string,
    private serverCurrentTime: number,
    private _tokenExpirationTime: number
  ) {}

  get token() {
    // if (this.serverCurrentTime < this.tokenExpirationTime) return this._token;
    return this._token;
  }

  get tokenExpirationTime() {
    return this._tokenExpirationTime;
  }
}
