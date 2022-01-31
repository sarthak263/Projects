import wikipedia
import time

class WikiWebCrawler:
    WordsCountDic = {}
    data = None
    def __init__(self,pageName):
        self.pageName = pageName

    def get_WikiContent(self):
        return wikipedia.page(self.pageName).content

    def splitContents(self):
        self.data = self.get_WikiContent()
        self.data = self.data.split("===")

    def countHistoryWords(self):
        self.splitContents()

        for i in range(2,14):
            for words in self.data[i].split(" "):
                if words in self.WordsCountDic:
                    self.WordsCountDic[words]+=1
                else:
                    self.WordsCountDic[words]=1

    def getWordCount(self):
        self.countHistoryWords()
        for key in sorted(self.WordsCountDic, key=self.getKey, reverse=True)[:10]:
            print("{0}: {1}".format(key,self.WordsCountDic[key]))


    def getKey(self, k):
        return self.WordsCountDic[k]

start = time.time()
wc = WikiWebCrawler("Microsoft")
wc.getWordCount()
end = time.time()
print(f"Runtime of the program is {end-start}")