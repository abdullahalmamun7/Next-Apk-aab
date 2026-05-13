# 🚀 GitHub Actions স্বয়ংক্রিয় বিল্ড এবং রিলিজ গাইড

## 📋 সেটআপ নির্দেশাবলী

### Step 1: কিস্টোর ফাইল Base64 এনকোড করুন

টার্মিনালে এই কমান্ড চালান (আপনার কম্পিউটারে):

```bash
base64 -w 0 signing.keystore > keystore.base64.txt
```

`keystore.base64.txt` ফাইল খুলুন এবং সম্পূর্ণ কন্টেন্ট কপি করুন।

### Step 2: GitHub Secrets যোগ করুন

আপনার GitHub রিপোজিটরিতে যান:
1. **Settings** → **Secrets and variables** → **Actions**
2. **New repository secret** এ ক্লিক করুন

এই 5টি secret যোগ করুন:

| Name | Value |
|------|-------|
| `KEYSTORE_FILE` | keystore.base64.txt-এর কন্টেন্ট |
| `KEYSTORE_PASSWORD` | `uj3Q_YsfxyK5` |
| `KEY_ALIAS` | `my-key-alias` |
| `KEY_PASSWORD` | `uj3Q_YsfxyK5` |
| `PLAY_STORE_SERVICE_ACCOUNT` | Google Play Service Account JSON |

### Step 3: Play Store Service Account সেটআপ করুন

1. **Google Play Console** খুলুন
2. **Settings** → **Developer account** → **API access**
3. **Create new service account** (বা existing ব্যবহার করুন)
4. JSON কী ডাউনলোড করুন
5. সম্পূর্ণ JSON কন্টেন্ট `PLAY_STORE_SERVICE_ACCOUNT` secret-এ পেস্ট করুন

### Step 4: বিল্ড ট্রিগার করুন

#### Option A: Git Tag ব্যবহার করে (সুপারিশকৃত)

```bash
# আপনার প্রজেক্ট ডিরেক্টরিতে:
git tag v2.1
git push origin v2.1
```

#### Option B: GitHub UI থেকে ম্যানুয়ালি

1. রিপোজিটরি → **Actions**
2. **Build and Release APK & AAB** workflow নির্বাচন করুন
3. **Run workflow** ক্লিক করুন

---

## 🔄 ওয়ার্কফ্লো কী করে

✅ **Checkout:** আপনার সর্বশেষ কোড পায়  
✅ **Setup Java 17:** বিল্ড এনভায়রনমেন্ট প্রস্তুত করে  
✅ **Decode Keystore:** Secret থেকে keystore ডিকোড করে  
✅ **Build APK:** স্বাক্ষরিত APK তৈরি করে  
✅ **Build AAB:** স্বাক্ষরিত AAB Bundle তৈরি করে  
✅ **Upload to Releases:** APK GitHub Releases-এ আপলোড করে  
✅ **Upload to Play Store:** AAB Play Store-এ আপলোড করে  
✅ **Cleanup:** সংবেদনশীল ফাইল মুছে ফেলে  

---

## 📝 Version আপডেট করা

পরবর্তী রিলিজের জন্য `app/build.gradle` এ পরিবর্তন করুন:

```gradle
android {
    defaultConfig {
        versionCode 22        // ১ বৃদ্ধি করুন
        versionName "2.2"     // আপডেট করুন
    }
}
```

তারপর:
```bash
git tag v2.2
git push origin v2.2
```

---

## 🎯 Play Store ট্র্যাক সেটিংস

`build-and-release.yml` ফাইলে এই লাইন দেখুন:

```yaml
track: internal
status: draft
```

### ট্র্যাক অপশন:
- `internal` → শুধুমাত্র টেস্টার দেখতে পারবে
- `alpha` → সীমিত টেস্টিং গ্রুপ
- `beta` → বৃহত্তর টেস্টিং গ্রুপ
- `production` → সর্বজনীন রিলিজ

### স্ট্যাটাস অপশন:
- `draft` → খসড়া (হাতে-কলমে পাবলিশ করতে হবে)
- `inProgress` → পর্যালোচনায়
- `completed` → স্বয়ংক্রিয়ভাবে লাইভ হয়

---

## 🐛 ট্রাবলশুটিং

### Problem: Build ব্যর্থ হয়েছে

**সমাধান:**
1. GitHub Actions লগ চেক করুন
2. সমস্ত secrets সঠিক আছে কি যাচাই করুন
3. Keystore পাসওয়ার্ড সঠিক কি নিশ্চিত করুন
4. Play Store Service Account বৈধ কি পরীক্ষা করুন

### Problem: APK স্বাক্ষর ব্যর্থ

**সমাধান:**
```bash
# Keystore ফাইল পরীক্ষা করুন
keytool -list -v -keystore signing.keystore -storepass uj3Q_YsfxyK5
```

### Problem: Play Store আপলোড ব্যর্থ

**সমাধান:**
1. Service Account-এর সঠিক অনুমতি আছে কি চেক করুন
2. Play Console-এ `next.isbest` অ্যাপ্লিকেশন তৈরি আছে কি নিশ্চিত করুন
3. Service Account-কে অ্যাপ্লিকেশন এডিটর হিসেবে যোগ করুন

---

## 📱 রিলিজের পরে

1. **GitHub Releases**: APK ডাউনলোড করুন এবং পরীক্ষা করুন
2. **Play Console**: Internal track-এ draft অবস্থায় আছে
3. পরীক্ষা সম্পন্ন হলে Play Console-এ যান এবং **Review** → **Publish** ক্লিক করুন

---

## ✅ চেকলিস্ট

- [ ] সকল 5টি Secrets যোগ করা হয়েছে
- [ ] Keystore file base64 এনকোড করা হয়েছে
- [ ] Play Store Service Account JSON যোগ করা হয়েছে
- [ ] Version numbers আপডেট করা হয়েছে (`app/build.gradle`)
- [ ] Git tag তৈরি করা হয়েছে (`git tag v2.1`)
- [ ] GitHub Actions ওয়ার্কফ্লো চলেছে
- [ ] APK GitHub Releases-এ পাওয়া যাচ্ছে
- [ ] AAB Play Store-এ পাওয়া যাচ্ছে

---

## 🎉 Happy Releasing!

এখন থেকে প্রতিটি git tag এর সাথে স্বয়ংক্রিয়ভাবে APK এবং AAB তৈরি হবে এবং প্রকাশিত হবে!

**প্রশ্ন থাকলে GitHub Issues খুলুন।**
