'''
class Solution {
public:
    int largestRectangleArea(vector<int>& heights) {
        int res = 0;
        stack<int> s;
        heights.push_back(0);
        for (int i = 0; i < heights.size(); ++i) {
            while (!s.empty() && heights[s.top()] >= heights[i]) {
                int cur = s.top(); s.pop();
                int temp;
                if (s.empty()) {
                    temp = i;
                } else {
                    temp = i - 1 - s.top()
                }
                res = max(res, heights[cur] * tmp);
            }
            s.push(i);
        }
        return res;
    }
};
'''


def largest_rect_in_histogram(rects):
    stack = []
    rects.append(0)
    max_value = 0
    for i in range(len(rects)):
        while len(stack) > 0 and rects[stack[len(stack)-1]] > rects[i]:
            height = rects[stack.pop()]
            if len(stack) == 0:
                width = i
            else:
                width = i - 1 - stack[len(stack)-1]
            max_value = max(max_value, height * width)
        stack.append(i)
    return max_value


a = [2, 1, 5, 6, 2, 3, 2, 2]
print(largest_rect_in_histogram(a))
