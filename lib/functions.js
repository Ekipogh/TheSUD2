var GameCharacter = Packages.ru.ekipogh.sud.objects.GameCharacter;
var Item =          Packages.ru.ekipogh.sud.objects.Item;
var Location =      Packages.ru.ekipogh.sud.objects.Location;
var Script =        Packages.ru.ekipogh.sud.Script;

function getLocationById(locationId){
    for(i = 0; i < locations.size(); i++){
        if(locations.get(i).getId()==locationId)
            return locations.get(i);
    }
}

function getItemById(itemId){
    for(i = 0; i < items.size(); i++){
        if(items.get(i).getId()==itemId)
            return items.get(i);
    }
}

function getCharacterById(charId){
    if(charId == player.getId()){
        return player;
    }
    for(i = 0; i < characters.size(); i++){
            if(characters.get(i).getId()==charId)
                return characters.get(i);
        }
}

function addItemToLocation(itemId, locationId){
    if(!isNaN(itemId) && !isNaN(locationId)){
        var location = getLocationById(locationId);
        var item = getItemById(itemId);
        location.addItem(item);
        game.updateItems();
    }
}

function magic(text){
    var re = /<\s*img\s+src\s*=\s*\"(.*)\">/g;
    var subst = '$1';
    return text.replace(re, "<img src = \"file:/"+gameDir+"\\"+subst+"\">");
}

function spawnCharacter(charId){
    if(!isNaN(charId)){
        var char = getCharacterById(charId);
        var currentLocation = player.getLocation();
        char.setLocation(currentLocation);
        game.updateCharacters();
    }
}

function parser(text){
    var re = /<<(.*?)>>/g;
    return text.replace(re, function(s,m1){return eval(m1);});
}

function isFunctionTest(name){
    var type = typeof this[name]
    return type == "function";
}

function getLocationsByCategory(categoryName){
    var toReturn = [];
    for(i = 0; i < locations.size(); i++){
        if(locations.get(i).getCategory(categoryName)!=null){
            toReturn.push(locations.get(i));
        }
    }
    return toReturn;
}

function getItemsByCategory(categoryName){
    var toReturn = [];
    for(i = 0; i < items.size(); i++){
        if(items.get(i).getCategory(categoryName)!=null){
            toReturn.push(items.get(i));
        }
    }
    return toReturn;
}

function getCharactersByCategory(categoryName){
    var toReturn = [];
    for(i = 0; i < characters.size(); i++){
        if(characters.get(i).getCategory(categoryName)!=null){
            toReturn.push(characters.get(i));
        }
    }
    return toReturn;
}

function update(){
    game.update();
}

function updateItems(){
    game.updateItems();
}

function updateCharacters(){
    game.updateCharacters();
}

function updatePlayer(){
    game.updatePlayer();
}
