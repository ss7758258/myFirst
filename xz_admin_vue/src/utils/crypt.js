
export default {
  decrypt(str){
    if(!str){
      return null;
    }
    let result = ""
    for(var i= 0;i<str.length;i++){
      result+=String.fromCharCode((str[i].charCodeAt()-(i%6)));
    }
    return result;
  }
}
