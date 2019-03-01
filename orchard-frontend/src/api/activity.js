const fetch = require("node-fetch");

export async function getActivityByUser() {
  const q = await fetch(`http://localhost:9001/users/YoshinoriN`)
    .then(function(response) {
      return response.json();
    });
  return JSON.stringify(q);
}
