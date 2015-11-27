var GameCharacter = Packages.ru.ekipogh.sud.GameCharacter;
var Item = Packages.ru.ekipogh.sud.Item;
var Location = Packages.ru.ekipogh.sud.GameCharacter;

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

function magic(){
    var re = /<\s*img\s+src\s*=\s*\"(.*)\">/g;
    var subst = '$1';
    return caller.replace(re, "<img src = \"file:/"+gameDir+"\\"+subst+"\">");
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

