const express = require('express')
const app = express()
const path = require('path')
const port = 8080
const axios = require('axios');
const DEMO_KEY = "35ee8770a2a23594c62994cc43fa2d61";


app.use(express.static(path.join(__dirname,'/dist/my-app')));

app.get('/api/searchResults/', (req, res) => {
	var states = [];
  var input = req.query.params.trim();
  if(input==undefined || input=="" || input.length==0){
	  return [];
	}
	axios.get('https://api.themoviedb.org/3/search/multi?api_key=35ee8770a2a23594c62994cc43fa2d61&language=en-US&query='+input)
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  for(var i=0;i<resData.length;i++){
		if(count==20)
		  break;
		var x = resData[i];
		var jsonObj = {}
		if(x["backdrop_path"]==undefined || x["backdrop_path"]==null || x["backdrop_path"]=="")
			continue;
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		if(x["media_type"]=="movie"){
		  jsonObj['title'] = x["title"];
		}
		else if(x["media_type"]=="tv"){
		  jsonObj['title'] = x["name"];
		}
		else
			continue;
		jsonObj['id'] = x["id"];
	    jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["backdrop_path"];
		jsonObj["media_type"]=x["media_type"];
		jsonObj["poster_path"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		if(jsonObj["media_type"].toUpperCase() == "MOVIE"){
		if("release_date" in x && x["release_date"]!="")
			jsonObj['release_date']=x['release_date'].substring(0,4);
		}
		else{
			 if("first_air_date" in x && x["first_air_date"]!="")
				jsonObj['release_date']=x['first_air_date'].substring(0,4);
		}		
	  if("vote_average" in x && x["vote_average"]!="")
		  jsonObj['vote_average']=x['vote_average'];
		states.push(jsonObj);
		count++;
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})


app.get('/api/trendingMovies/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  for(var i=0;i<resData.length;i++){
		if(count==6)
		  break;
		var x = resData[i];
		var jsonObj = {}
	    if(x["backdrop_path"]==undefined || x["backdrop_path"]==null || x["backdrop_path"]=="")
			continue;
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/original"+x["backdrop_path"];
		jsonObj["media_type"]="movie";
		jsonObj["poster_path"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		states.push(jsonObj);
		count++;
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})


app.get('/api/popularMovies/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
		  if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {}
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/popularMovies1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {}		
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/topRatedMovies/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {}
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/topRatedMovies1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/trendingMoviesAll/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/trending/movie/day?api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/trendingMoviesAll1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/trending/movie/day?api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/popularTVShows/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/tv/popular?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/popularTVShows1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/tv/popular?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/topRatedTVShows/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/tv/top_rated?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/topRatedTVShows1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/tv/top_rated?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/trendingTVShows/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/trending/tv/day?api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/trendingTVShows1/', (req, res) => {
	var states = [];
	axios.get('https://api.themoviedb.org/3/trending/tv/day?api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})



app.get('/api/recommendedMovies/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/recommendations?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/recommendedMovies1/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/recommendations?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})


app.get('/api/similarMovies/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/similar?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/similarMovies1/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/similar?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["title"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="movie";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})


app.get('/api/recommendedTV/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/recommendations?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/recommendedTV1/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/recommendations?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})


app.get('/api/similarTV/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/similar?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  var count=0;
	  var storeArray = [];
	  for(var i=0;i<resData.length;i++){
		  if(count!=0 && count%6==0){
			  states.push(storeArray);
			  storeArray = [];
			}
			if(count==24)
			  break;
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		count++;
	  }
	  if(storeArray.length!=0)
		  states.push(storeArray);
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/similarTV1/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/similar?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==24)
			  break;
		var storeArray = [];
		var x = resData[i];
		if(x["poster_path"]==undefined || x["poster_path"]==null || x["poster_path"]=="")
			continue;
		var jsonObj = {};
		jsonObj['title'] = x["name"];
		jsonObj['id'] = x["id"];
		jsonObj["image"]="https://image.tmdb.org/t/p/w500"+x["poster_path"];
		jsonObj["media_type"]="tv";
		storeArray.push(jsonObj);
		states.push(storeArray);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})




app.get('/api/getMovieVideo/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/videos?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  if(resData!=undefined && resData.length!=0){
		  for(var i=0;i<resData.length;i++){
			  var data = resData[i];
			  if('site' in data && data['site']!="" && data['site'].toLowerCase()=="youtube"){
				  if('type' in data && data['type']!="" && data['type'].toLowerCase().includes("trailer")){
					  states['site']=data['site'];
					  states['type']=data['type'];
					  states['key']=data['key'];
					  states['name']=data['name'];
					  break;
				  }
			  }
		  }
		  if(Object.keys(states).length==0){
			  for(var i=0;i<resData.length;i++){
			  var data = resData[i];
			  if('site' in data && data['site']!="" && data['site'].toLowerCase()=="youtube"){
				  if('type' in data && data['type']!="" && data['type'].toLowerCase().includes("teaser")){
					  states['site']=data['site'];
					  states['type']=data['type'];
					  states['key']=data['key'];
					  states['name']=data['name'];
					  break;
					}
				}
			}
		  }
		  if(Object.keys(states).length==0){
			  states['site']="YouTube";
			  states['type']="";
			  states['key']="tzkWB85ULJY";
			  states['name']="";
		  }
	  }
	  else{
		  states['site']="YouTube";
		  states['type']="";
		  states['key']="tzkWB85ULJY";
		  states['name']="";
	  }
	  
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})

function timeConvert(n) {
if(n<60){
	return n + "mins";
}
var num = n;
var hours = (num / 60);
var rhours = Math.floor(hours);
var minutes = (hours - rhours) * 60;
var rminutes = Math.round(minutes);
if(rminutes==0){
	return rhours + "hrs ";
}
return rhours + "hrs " + rminutes + "mins";
}

app.get('/api/getMovieBackgroundImage/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  states['image'] = "https://image.tmdb.org/t/p/w500"+resData['backdrop_path'];
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/getTVBackgroundImage/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  states['image'] = "https://image.tmdb.org/t/p/w500"+resData['backdrop_path'];
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
  
})

app.get('/api/getMovieDetails/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  states['id']=movieId;
	  if("title" in resData && resData["title"]!="")
		  states['title']=resData['title'];
	  var genres = resData['genres'];
	  if(genres!=undefined && genres.length!=0){
		  var genreList=genres[0]['name'];
		  for(var i=1;i<genres.length;i++){
			  genreList+=", ";
			  genreList+=genres[i]['name'];
		  }
		  states['genres']=genreList;
	  }
	  
	  languages = resData['spoken_languages'];
	  var languageList=[];
	  if(languages!=undefined && languages.length!=0){
		  var languageList=languages[0]['english_name'];
		  for(var i=1;i<languages.length;i++){
			  languageList+=", ";
			  languageList+=languages[i]['english_name'];
		  }
		  states['spoken_languages']=languageList;
	  }
	  if("release_date" in resData && resData["release_date"]!="")
		  states['release_date']=resData['release_date'].substring(0,4);
	  if("runtime" in resData && resData["runtime"]!="")
		  states['runtime']=timeConvert(resData['runtime']);
	  if("overview" in resData && resData["overview"]!="")
		  states['overview']=resData['overview'];
	  if("vote_average" in resData && resData["vote_average"]!="")
		  states['vote_average']=resData['vote_average'];
	  if("tagline" in resData && resData["tagline"]!="")
		  states['tagline']=resData['tagline'];
	  if("poster_path" in resData && resData["poster_path"]!=null && resData["poster_path"]!="")
		  states['poster_path']="https://image.tmdb.org/t/p/w500"+resData['poster_path'];
		else
			states['poster_path']="https://cinemaone.net/images/movie_placeholder.png";
	  states['media_type']="Movie";
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})

app.get('/api/getMovieCast/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	//var movieId = "464052";
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/credits?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.cast;
	  for(var i=0;i<resData.length;i++){
		  var data = resData[i];
		  if(data['profile_path']==undefined || data['profile_path']=="")
			  continue;
		  var jsonObj = {};
		  jsonObj['id']=data['id'];
		  jsonObj['name']=data['name'];
		  jsonObj['character']=data['character'];
		  jsonObj['profile_path']="https://image.tmdb.org/t/p/w500/"+data['profile_path'];
		  states.push(jsonObj);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
})

app.get('/api/getMovieReviews/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/movie/'+movieId+'/reviews?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==10)
			  break;
		  var data = resData[i];
		  var jsonObj = {};
		  var authorDetails = data['author_details'];
		  if(data['author']!=undefined && data['author']!="")
			  jsonObj['author']=data['author'];
	      if(data['content']!=undefined && data['content']!="")
			  jsonObj['content']=data['content'];
		  if(data['created_at']!=undefined && data['created_at']!="")
			  jsonObj['created_at']=data['created_at'];
		  if(data['url']!=undefined && data['url']!="")
			  jsonObj['url']=data['url'];
		  if(authorDetails['rating']==undefined)
			  jsonObj['rating'] = 0;
		  else
			 jsonObj['rating'] = authorDetails['rating'];
		  if(authorDetails['avatar_path']!=undefined && authorDetails['avatar_path']!=""){
			  if(authorDetails['avatar_path'].includes("/https")){
				  jsonObj['avatar_path']=authorDetails['avatar_path'].substring(1,authorDetails['avatar_path'].length);
			  }
			  else
			  jsonObj['avatar_path']="https://image.tmdb.org/t/p/original/"+authorDetails['avatar_path'];
		  }
		  states.push(jsonObj);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
})



app.get('/api/getCastDetails/', (req, res) => {
	var states = {};
	var castId = req.query.params;
	axios.get('https://api.themoviedb.org/3/person/'+castId+'?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  if('birthday' in resData && resData['birthday']!=undefined && resData['birthday']!="")
		states['birthday']=resData['birthday'];
	  if('gender' in resData && resData['gender']!=undefined && resData['gender']!=""){
		  if(resData['gender']=="1")
			  states['gender']="Female";
		  else if(resData['gender']=="2")
			  states['gender']="Male";
		  else
			  states['gender']="Undefined";
	  }
	  if('name' in resData && resData['name']!=undefined && resData['name']!="")
		  states['name']=resData['name'];
	  if('homepage' in resData && resData['homepage']!=undefined && resData['homepage']!="")
		  states['homepage']=resData['homepage'];
	  if('also_known_as' in resData && resData['also_known_as']!=undefined && resData['also_known_as'].length!=0)
		  states['also_known_as']=resData['also_known_as'];
	  if('known_for_department' in resData && resData['known_for_department']!=undefined && resData['known_for_department'].length!=0)
		  states['known_for_department']=resData['known_for_department'];
      if('biography' in resData && resData['biography']!=undefined && resData['biography']!="")
		  states['biography']=resData['biography'];
	  if('place_of_birth' in resData && resData['place_of_birth']!=undefined && resData['place_of_birth']!="")
		  states['place_of_birth']=resData['place_of_birth'];
	  if('homepage' in resData && resData['homepage']!=undefined && resData['homepage']!="")
		  states['homepage']=resData['homepage'];
	  if('profile_path' in resData && resData['profile_path']!=undefined && resData['profile_path']!="")
		  states['profile_path']="https://image.tmdb.org/t/p/w500/"+resData['profile_path'];
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})

app.get('/api/getCastExternalIds/', (req, res) => {
	var states = {};
	var castId = req.query.params;
	axios.get('https://api.themoviedb.org/3/person/'+castId+'/external_ids?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  if('imdb_id' in resData && resData['imdb_id']!=undefined && resData['imdb_id']!="")
		states['imdb_id']=resData['imdb_id'];
	  if('facebook_id' in resData && resData['facebook_id']!=undefined && resData['facebook_id']!="")
		states['facebook_id']=resData['facebook_id'];
      if('instagram_id' in resData && resData['instagram_id']!=undefined && resData['instagram_id']!="")
		states['instagram_id']=resData['instagram_id'];
      if('twitter_id' in resData && resData['twitter_id']!=undefined && resData['twitter_id']!="")
		states['twitter_id']=resData['twitter_id'];
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})


app.get('/api/getTVVideo/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/videos?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  if(resData!=undefined && resData.length!=0){
		  for(var i=0;i<resData.length;i++){
			  var data = resData[i];
			  if('site' in data && data['site']!="" && data['site'].toLowerCase()=="youtube"){
				  if('type' in data && data['type']!="" && data['type'].toLowerCase().includes("trailer")){
					  states['site']=data['site'];
					  states['type']=data['type'];
					  states['key']=data['key'];
					  states['name']=data['name'];
					  break;
				  }
			  }
		  }
		  if(Object.keys(states).length==0){
			  for(var i=0;i<resData.length;i++){
			  var data = resData[i];
			  if('site' in data && data['site']!="" && data['site'].toLowerCase()=="youtube"){
				  if('type' in data && data['type']!="" && data['type'].toLowerCase().includes("teaser")){
					  console.log("teaserrr");
					  states['site']=data['site'];
					  states['type']=data['type'];
					  states['key']=data['key'];
					  states['name']=data['name'];
					  break;
					}
				}
			}
		  }
		  if(Object.keys(states).length.length==0){
			  states['site']="YouTube";
			  states['type']="";
			  states['key']="tzkWB85ULJY";
			  states['name']="";
		  }
	  }
	  else{
		  states['site']="YouTube";
		  states['type']="";
		  states['key']="tzkWB85ULJY";
		  states['name']="";
	  }
	  
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})


app.get('/api/getTVDetails/', (req, res) => {
	var states = {};
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data;
	  states['id']=movieId;
	  if("name" in resData && resData["name"]!="")
		  states['title']=resData['name'];
	  var genres = resData['genres'];
	  if(genres!=undefined && genres.length!=0){
		  var genreList=genres[0]['name'];
		  for(var i=1;i<genres.length;i++){
			  genreList+=", ";
			  genreList+=genres[i]['name'];
		  }
		  states['genres']=genreList;
	  }
	  
	  languages = resData['spoken_languages'];
	  var languageList=[];
	  if(languages!=undefined && languages.length!=0){
		  var languageList=languages[0]['english_name'];
		  for(var i=1;i<languages.length;i++){
			  languageList+=", ";
			  languageList+=languages[i]['english_name'];
		  }
		  states['spoken_languages']=languageList;
	  }
	  if("first_air_date" in resData && resData["first_air_date"]!="")
		  states['release_date']=resData['first_air_date'].substring(0,4);
	  if("episode_run_time" in resData && resData["episode_run_time"]!=undefined && resData["episode_run_time"].length!=0)
		  states['runtime']=timeConvert(resData['episode_run_time'][0]);
	  if("overview" in resData && resData["overview"]!="")
		  states['overview']=resData['overview'];
	  if("vote_average" in resData && resData["vote_average"]!="")
		  states['vote_average']=resData['vote_average'];
	  if("tagline" in resData && resData["tagline"]!="")
		  states['tagline']=resData['tagline'];
	  if("poster_path" in resData && resData["poster_path"]!=null && resData["poster_path"]!="")
		  states['poster_path']="https://image.tmdb.org/t/p/w500"+resData['poster_path'];
		else
			states['poster_path']="https://cinemaone.net/images/movie_placeholder.png";
	  states['media_type']="TV";
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send({});
  });
})

app.get('/api/getTVCast/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/credits?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.cast;
	  for(var i=0;i<resData.length;i++){
		  var data = resData[i];
		  if(data['profile_path']==undefined || data['profile_path']=="")
			  continue;
		  var jsonObj = {};
		  jsonObj['id']=data['id'];
		  jsonObj['name']=data['name'];
		  jsonObj['character']=data['character'];
		  jsonObj['profile_path']="https://image.tmdb.org/t/p/w500/"+data['profile_path'];
		  states.push(jsonObj);
			
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
})


app.get('/api/getTVReviews/', (req, res) => {
	var states = [];
	var movieId = req.query.params;
	//var movieId = "464052";
	axios.get('https://api.themoviedb.org/3/tv/'+movieId+'/reviews?language=en-US&page=1&api_key=35ee8770a2a23594c62994cc43fa2d61')
  .then(response => {
	  var resData = response.data.results;
	  for(var i=0;i<resData.length;i++){
		  if(i==10)
			  break;
		  var data = resData[i];
		  var jsonObj = {};
		  var authorDetails = data['author_details'];
		  if(data['author']!=undefined && data['author']!="")
			  jsonObj['author']=data['author'];
	      if(data['content']!=undefined && data['content']!="")
			  jsonObj['content']=data['content'];
		  if(data['created_at']!=undefined && data['created_at']!="")
			  jsonObj['created_at']=data['created_at'];
		  if(data['url']!=undefined && data['url']!="")
			  jsonObj['url']=data['url'];
		  if(authorDetails['rating']==undefined || authorDetails['rating']=="")
			  jsonObj['rating'] = 0;
		  else
			 jsonObj['rating'] = authorDetails['rating'];
		  if(authorDetails['avatar_path']!=undefined && authorDetails['avatar_path']!=""){
			  if(authorDetails['avatar_path'].includes("/https")){
				  jsonObj['avatar_path']=authorDetails['avatar_path'].substring(1,authorDetails['avatar_path'].length);
			  }
			  else
			  jsonObj['avatar_path']="https://image.tmdb.org/t/p/original/"+authorDetails['avatar_path'];
		  }
		  states.push(jsonObj);
	  }
	res.send(states);
  })
  .catch(error => {
    console.log(error);
	res.send([]);
  });
})

app.use('/*', (req, res) => {
	res.sendFile(path.join(__dirname+'/dist/my-app/index.html'));
})



app.listen(port, () => {
  console.log('Example app listening at http://localhost:${port}')
})

module.exports=app;