package edu.nau.coe_stic_app.models;

import java.util.Date;

public class RequirementInstance
{
   private int id;
   private int req_id;
   private String uid;
   private String status;
   private String doc_guid;
   private Date retake_date;

   public RequirementInstance(int id, int req_id, String uid, String status, String doc_guid, Date retake_date)
   {
      this.id = id;
      this.req_id = req_id;
      this.uid = uid;
      this.status = status;
      this.doc_guid = doc_guid;
      this.retake_date = retake_date;
   }

   public int getID()
   {
      return this.id;
   }

   public int getReqID()
   {
      return this.req_id;
   }

   public String getUID()
   {
      return this.uid;
   }

   public String getStatus()
   {
      return this.status;
   }

   public String getDocGUID()
   {
      return this.doc_guid;
   }

   public Date getRetakeDate()
   {
      return this.retake_date;
   }
}
