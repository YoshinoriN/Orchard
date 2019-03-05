const fetch = require("node-fetch");

class UserEvent {

  constructor(_userName) {
    if (_userName.length == 0) {
      throw new Error('Must be specify _userName.');
    }
    this._userName = _userName;
  }

  async getActivity() {
    const q = await fetch(`http://localhost:9001/users/${this._userName}/activity?from=0`)
      .then(function(response) {
        return response.json();
      });
    return JSON.stringify(q);
  }

  async getFirstTimeEvent() {
    const q = await fetch(`http://localhost:9001/users/${this._userName}/first-time-event`)
      .then(function(response) {
        return response.json();
      });
    return JSON.stringify(q);
  }

  async getStatistics() {
    const q = await fetch(`http://localhost:9001/users/${this._userName}/statistics`)
      .then(function(response) {
        return response.json();
      });
    return JSON.stringify(q);
  }

  async getContributedRepositories() {
    const q = await fetch(`http://localhost:9001/users/${this._userName}/contributions`)
      .then(function(response) {
        return response.json();
      });
    return JSON.stringify(q);
  }

}

module.exports = UserEvent;
