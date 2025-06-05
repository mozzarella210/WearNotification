package com.websarva.wings.android.wearnotification.firebase

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    /*
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchFilterItemsByDepartments(
        allowedDepartments: List<String>,
        situationValue: Int
    ): List<Item> {
        return try {
            val tasks = allowedDepartments.map { deptId ->
                db.collection("departments")
                    .document(deptId)
                    .collection("items")
                    .whereEqualTo("situation", situationValue)
                    .get()
            }

            val snapshots = Tasks.whenAllSuccess<QuerySnapshot>(tasks).await()

            val allItems = snapshots.flatMap { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Item::class.java)?.copy(id = doc.id)
                }
            }

            allItems
        } catch (e: Exception) {
            Log.e("ItemRepo", "❌ fetch失敗: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun fetchNotEqualSituationItemsByDepartments(
        allowedDepartments: List<String>,
        situationValue: Int
    ): List<Item> {
        return try {
            val tasks = allowedDepartments.map { deptId ->
                db.collection("departments")
                    .document(deptId)
                    .collection("items")
                    .whereNotEqualTo("situation", situationValue)
                    .get()
            }

            val snapshots = Tasks.whenAllSuccess<QuerySnapshot>(tasks).await()

            snapshots.flatMap { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Item::class.java)?.copy(id = doc.id)
                }
            }
        } catch (e: Exception) {
            Log.e("ItemRepo", "❌ fetch失敗: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun updateItem(
        departmentId: String,
        itemId: String,
        updatedItem: Item
    ): Boolean {
        Log.d("UpdateItem", "🔥 Firestore更新開始: dept=$departmentId, id=$itemId")


        return try {
            db.collection("departments")
                .document(departmentId)
                .collection("items")
                .document(itemId)
                .set(updatedItem, SetOptions.merge())
                .await()
            Log.d("UpdateItem", "✅ 成功")

            true
        } catch (e: Exception) {
            Log.e("UpdateItem", "❌ 失敗")
            false
        }
    }

    suspend fun addItem(
        departmentId: String,
        item: Item
    ): Boolean {
        return try {
            val id = item.id.ifEmpty { db.collection("departments").document().id }
            val newItem = item.copy(id = id)

            db.collection("departments")
                .document(departmentId)
                .collection("items")
                .document(id)
                .set(newItem)
                .await()

            true
        } catch (e: Exception) {
            Log.e("ItemRepo", "❌ addItem失敗: ${e.message}", e)
            false
        }
    }

     */
}
