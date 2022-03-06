package com.unique.blockchain.nft.domain.node;

import com.unique.blockchain.nft.domain.BaseBean;

import java.util.List;

public class AllMoneyBean extends BaseBean {

   private Rewards rewards;

    public Rewards getRewards() {
        return rewards;
    }

    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }

    public class Rewards{
       private List<Rewardss> rewards;

       public List<Rewardss> getRewards() {
           return rewards;
       }

       public void setRewards(List<Rewardss> rewards) {
           this.rewards = rewards;
       }

       public class Rewardss{

           private String amount;
           private String denom;

           public String getAmount() {
               return amount;
           }

           public void setAmount(String amount) {
               this.amount = amount;
           }

           public String getDenom() {
               return denom;
           }

           public void setDenom(String denom) {
               this.denom = denom;
           }
       }
   }

}
