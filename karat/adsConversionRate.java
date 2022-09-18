package karat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class adsConversionRate {
    public static void main(String[] args) {
        // The people who buy ads on our network don't have enough data about how ads are working for
    //their business. They've asked us to find out which ads produce the most purchases on their website.

    // Our client provided us with a list of user IDs of customers who bought something on a landing page
    //after clicking one of their ads:

    // # Each user completed 1 purchase.
    // completed_purchase_user_ids = [
    //   "3123122444","234111110", "8321125440", "99911063"]

    // And our ops team provided us with some raw log data from our ad server showing every time a
    //user clicked on one of our ads:
    // ad_clicks = [
    //  #"IP_Address,Time,Ad_Text",
    //  "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
    //  "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
    //  "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
    //  "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
    //  "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
    //  "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens",
    //]

    //The client also sent over the IP addresses of all their users.

    //all_user_ips = [
    //  #"User_ID,IP_Address",
    //   "2339985511,122.121.0.155",
    //  "234111110,122.121.0.1",
    //  "3123122444,92.130.6.145",
    //  "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
    //  "8321125440,82.1.106.8",
    //  "99911063,92.130.6.144"
    //]

    // Write a function to parse this data, determine how many times each ad was clicked,
    //then return the ad text, that ad's number of clicks, and how many of those ad clicks
    //were from users who made a purchase.


    // Expected output:
    // Bought Clicked Ad Text
    // 1 of 2  2017 Pet Mittens
    // 0 of 1  The Best Hollywood Coats
    // 3 of 3  Buy wool coats for your pets
        
        
        // 哈希，购买uid一个set
        // uid->ip一个map
        // ad text -> freq, total cnt 一个map
        // iter 点击input arr, 抽信息[ad text, ip] ，
        // 1. 根据ip查uid，uid是购买者则记录cnt
        // 2. 统计每个text 总点击次数
        
        String[] uids = new String[]{"3123122444","234111110", "8321125440", "99911063"};
        String[] ad_clicks = {"122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
  "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
  "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
  "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
  "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
  "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens"};
        String[] uid_ip = {"2339985511,122.121.0.155",
  "234111110,122.121.0.1",
  "3123122444,92.130.6.145",
  "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
  "8321125440,82.1.106.8",
  "99911063,92.130.6.144"};
        
        Set<String> uidSet = new HashSet<>();
        for(String id: uids) uidSet.add(id);
        
        Map<String,String> ipToId = new HashMap<>();
        for(String each: uid_ip){
            String[] temp = each.split(",");
            ipToId.put(temp[1], temp[0]);
        }
        
        Map<String, int[]> adInfo = new HashMap<>();
        for(String str: ad_clicks){
            String[] temp = str.split(",");
            String ip = temp[0];
            String text = temp[2];
            
            int[] arr = adInfo.getOrDefault(text, new int[2]);
            // update total cnt
            arr[0]++;
            // update purchase cnt
            String uid = ipToId.get(ip);
            if (uidSet.contains(uid)){
                arr[1]++;
            }
            adInfo.put(text, arr);
        }
        
        for(String text: adInfo.keySet()){
            int total = adInfo.get(text)[0];
            int bought = adInfo.get(text)[1];
            System.out.println(bought+" of "+total+" "+text);
        }
    }
}
